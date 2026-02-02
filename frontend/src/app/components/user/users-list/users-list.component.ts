import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { UserService } from '../../../services/user.service';
import { IUser } from '../../../types/user/IUser';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'email', 'actions'];
  dataSource = new MatTableDataSource<IUser>();

  constructor(private readonly _userService: UserService, private _snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this._userService.findAll().subscribe(response => {
      this.dataSource.data = response;
    });
  }

  deleteUser(id: number): void {
    this._userService.deleteUser(id).subscribe(() => {
      this.dataSource.data = this.dataSource.data.filter(user => user.id !== id);
      this._snackBar.open("Usuário deletado com sucesso.", "Fechar");
    });
  }
}
