import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from "./components/main/main.component";
import {ChitchatRefComponent} from "./components/chitchat-ref/chitchat-ref.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {BrowserModule} from "@angular/platform-browser";
import {ProfileComponent} from "./components/profile/profile.component";
import {EmailConfirmComponent} from "./components/email-confirm/email-confirm.component";
import {
  PasswordRecoveryComponent
} from "./components/password-recovery/password-recovery.component";
import {OneChitchatComponent} from "./components/one-chitchat/one-chitchat.component";

const routes: Routes = [
  {path: '', component: MainComponent},
  {path: 'chitchat', component: ChitchatRefComponent},
  {path: 'one_chitchat', component: OneChitchatComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'click', component: EmailConfirmComponent},
  {path: 'password_recovery', component: PasswordRecoveryComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [BrowserModule, RouterModule.forRoot(routes,
      {
        useHash: true,
        scrollPositionRestoration: 'enabled'
      })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
