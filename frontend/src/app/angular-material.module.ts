import { MatFormFieldModule } from '@angular/material/form-field';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatRadioModule } from '@angular/material/radio';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatCardModule } from '@angular/material/card';
import { NgModule } from '@angular/core';

@NgModule(
    {
      imports: [MatCardModule, MatFormFieldModule, MatInputModule, MatTableModule, 
        MatSnackBarModule,MatIconModule,MatPaginatorModule, MatToolbarModule, 
        MatCheckboxModule, MatButtonModule, MatSidenavModule,MatSelectModule, MatRadioModule, MatListModule],

      exports: [MatCardModule, MatFormFieldModule, MatInputModule, 
      MatTableModule, MatSnackBarModule, MatIconModule,MatToolbarModule, MatTableModule,
      MatCheckboxModule, MatButtonModule, MatSidenavModule,MatSelectModule, MatRadioModule, MatListModule],
    }
  )
  export class AngularMaterialModule { }