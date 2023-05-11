import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from "./components/main/main.component";
import {ChitchatRefComponent} from "./components/chitchat-ref/chitchat-ref.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {BrowserModule} from "@angular/platform-browser";
import {LoginComponent} from "./auth/login/login.component";
import {ProfileComponent} from "./components/profile/profile.component";
import {EmailConfirmComponent} from "./components/email-confirm/email-confirm.component";

const routes: Routes = [
  {path: '', component: MainComponent},
  {path: 'chitchat', component: ChitchatRefComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'click', component: EmailConfirmComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [BrowserModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
