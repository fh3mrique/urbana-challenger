import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { BrowserModule } from "@angular/platform-browser";
import { AngularMaterialModule } from "../angular-material.module";
import { NavComponent } from './nav/nav.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from "../app-routing.module";
import { HeaderComponent } from './header/header.component';
import { UsersCreateComponent } from './user/users-create/users-create.component';
import { UsersListComponent } from "./user/users-list/users-list.component";
import { UserUpdateComponent } from './user/user-update/user-update.component';
import { UsersDeleteComponent } from './user/users-delete/users-delete.component';
import { PipesModule } from "../pipes/pipes.module";
import { UserCardsComponent } from './user/user-cards/user-cards.component';
@NgModule(
    {
        declarations: [
            NavComponent,
            HomeComponent,
            HeaderComponent,
            UsersCreateComponent,
            UsersListComponent,
            UserUpdateComponent,
            UsersDeleteComponent,
            UserCardsComponent
        ],
        imports: [
            FormsModule,
            ReactiveFormsModule,
            BrowserModule,
            AngularMaterialModule,
            AppRoutingModule,
            /* aula 37 11.22min */
            /* ToastrModule  */
            PipesModule
        ],
        exports: [
            AngularMaterialModule,
            AppRoutingModule,
            PipesModule
        ],
    }
)
export class ComponentsModule { }