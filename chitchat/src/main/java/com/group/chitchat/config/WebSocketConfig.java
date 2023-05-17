package com.group.chitchat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.chitchat.model.User;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.auth.JwtService;
import com.group.chitchat.service.internationalization.BundlesService;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.BasicUserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Log4j2
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  private final JwtService authenticationService;
  private final UserRepo userRepository;
  private final BundlesService bundlesService;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/socket")
        .setAllowedOriginPatterns("*")
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes("/app")
        .enableSimpleBroker("/message");
  }

  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(new ObjectMapper());
    converter.setContentTypeResolver(resolver);
    messageConverters.add(converter);
    return false;
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {

      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
            StompHeaderAccessor.class);

        log.info("in override " + accessor.getCommand());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {

          String authToken = accessor.getFirstNativeHeader("auth-token");

          log.info("Header auth token: " + authToken);

          String username = authenticationService.extractUsername(authToken);
          if (Objects.isNull(username)) {
            return null;
          }
          User user = userRepository.findByUsername(username)
              .orElseThrow(() -> new UsernameNotFoundException(

                  String.format(bundlesService
                          .getMessForLocale("e.not_exist",
                              Locale.getDefault()),
                      username))
              );
          if (!authenticationService.isTokenValid(authToken, user)) {
            return null;
          }
          accessor.setUser(new BasicUserPrincipal(username));
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

          if (Objects.nonNull(authentication)) {
            log.info("Disconnected Auth : " + authentication.getName());
          } else {
            log.info("Disconnected Sess : " + accessor.getSessionId());
          }
        }
        return message;
      }
    });
  }
}
