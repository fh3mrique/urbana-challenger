import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavComponent } from './components/nav/nav.component';
import { HomeComponent } from './components/home/home.component';
import { UsersListComponent } from './components/user/users-list/users-list.component';
import { UsersCreateComponent } from './components/user/users-create/users-create.component';
import { UserUpdateComponent } from './components/user/user-update/user-update.component';
import { UserCardsComponent } from './components/user/user-cards/user-cards.component';

const routes: Routes = [
  {
    path: '',
    component: NavComponent,
    children: [
      { path: '', redirectTo: 'users', pathMatch: 'full' },

      { path: 'home', component: HomeComponent },
      { path: 'users', component: UsersListComponent },
      { path: 'users/create', component: UsersCreateComponent },
      { path: 'users/update/:id', component: UserUpdateComponent },
      { path: 'users/:id/cartoes', component: UserCardsComponent },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
