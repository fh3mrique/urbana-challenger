import { Component , OnInit} from '@angular/core';
import { UsersService } from 'src/app/services/users-service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {


  constructor(
    private readonly _usersService: UsersService
  ) { }

  ngOnInit(): void {
  this._usersService.getUsers().subscribe((usersResponse) => {
    console.log(usersResponse);
  });
}

}
