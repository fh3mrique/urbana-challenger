import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StatusLabelPipe } from './status-label.pipe';
import { BoardingPassImagePipe } from './boarding-pass-image.pipe';



@NgModule({
  declarations: [
    StatusLabelPipe,
    BoardingPassImagePipe
  ],
  imports: [
    CommonModule
  ],
  exports:[
    StatusLabelPipe,
    BoardingPassImagePipe
  ]
})
export class PipesModule { }
