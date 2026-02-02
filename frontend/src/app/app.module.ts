import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { ComponentsModule } from './components/components.module';
import { HttpClient } from '@angular/common/http';
import { AngularMaterialModule } from './angular-material.module';
import { HttpClientModule } from '@angular/common/http';  // Import HttpClientModule
/* import { UsersListComponent } from './components/users-list/users-list.component'; */
@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule, 
    ComponentsModule,
    AngularMaterialModule, 
    HttpClientModule,
    
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
