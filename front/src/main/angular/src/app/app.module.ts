import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from './auth/login/login.component';
import {RegisterComponent} from './auth/register/register.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatDialogModule} from "@angular/material/dialog";
import {CategoryComponent} from "./components/category/category.component";
import {MatIconModule} from "@angular/material/icon";
import {ChitchatComponent} from './components/chitchat/chitchat.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {authInterceptorProviders} from "./service/auth-interceptor.service";
import {authErrorInterceptorProviders} from "./service/error-interceptor.service";
import {OneChitchatComponent} from './components/one-chitchat/one-chitchat.component';
import {AddNewChitchatComponent} from './components/add-new-chitchat/add-new-chitchat.component';
import {ChitchatFilterComponent} from './components/chitchat-filter/chitchat-filter.component';
import {MatNativeDateModule, MatOptionModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {ChitchatRefComponent} from './components/chitchat-ref/chitchat-ref.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {MainComponent} from './components/main/main.component';
import {TranslocoRootModule} from './transloco-root.module';
import {langInterceptorProviders} from "./service/lang-interceptor.service";
import {EmailConfirmComponent} from "./components/email-confirm/email-confirm.component";
import { PasswordRecoveryComponent } from "./components/password-recovery/password-recovery.component";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {ProfileComponent} from './components/profile/profile.component';
import { ProfileUserDataComponent } from './components/profile-user-data/profile-user-data.component';
import { ProfileUserChitchatsComponent } from './components/profile-user-chitchats/profile-user-chitchats.component';
import { ProfileUserStatisticsComponent } from './components/profile-user-statistics/profile-user-statistics.component';
import { ProfileUserChitchatsTableComponent } from './components/profile-user-chitchats-table/profile-user-chitchats-table.component';
import {MatTableModule} from "@angular/material/table";
import {MatSortModule} from "@angular/material/sort";
import {MatPaginatorModule} from "@angular/material/paginator";
import {NgOptimizedImage} from "@angular/common";
import {InternalChatComponent} from "./components/internal-chat/internal-chat.component";


const appRoutes: Routes = [

]

@NgModule({
  declarations: [
    EmailConfirmComponent,
    AppComponent,
    LoginComponent,
    RegisterComponent,
    CategoryComponent,
    ChitchatComponent,
    OneChitchatComponent,
    AddNewChitchatComponent,
    ChitchatFilterComponent,
    ChitchatRefComponent,
    NotFoundComponent,
    MainComponent,
    ProfileComponent,
    ProfileUserDataComponent,
    ProfileUserChitchatsComponent,
    ProfileUserStatisticsComponent,
    ProfileUserChitchatsTableComponent,
    PasswordRecoveryComponent,
    InternalChatComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSortModule,
    FormsModule,
    MatSnackBarModule,
    MatDialogModule,
    MatIconModule,
    MatNativeDateModule,
    MatOptionModule,
    MatSelectModule,
    MatDatepickerModule,
    MatInputModule,
    MatButtonModule,
    TranslocoRootModule,
    MatCheckboxModule,
    MatTableModule,
    MatSortModule,
    MatPaginatorModule,
    NgOptimizedImage,
  ],
  providers: [authInterceptorProviders,authErrorInterceptorProviders,langInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
