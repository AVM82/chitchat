import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {BrowserModule} from "@angular/platform-browser";
import {ChitchatRefComponent} from "./components/chitchat-ref/chitchat-ref.component";
import {MainComponent} from "./components/main/main.component";

const routes: Routes = [
  {path: 'chitchat', component: ChitchatRefComponent},
  {path: '', component: MainComponent},
  {path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [BrowserModule, RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
