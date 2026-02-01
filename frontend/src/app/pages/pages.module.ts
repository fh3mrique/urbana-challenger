import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComponentsModule } from '../components/components.module';
import { RouterLink, RouterOutlet } from '@angular/router';
import { HomePageComponent } from './home-page/home-page.component';




@NgModule({
    declarations: [
        HomePageComponent
    
  ],
    imports: [
        CommonModule,
        ComponentsModule,
        RouterLink,
        RouterOutlet
    ],
    exports: [
        ComponentsModule,
    ]
})
export class PagesModule { }