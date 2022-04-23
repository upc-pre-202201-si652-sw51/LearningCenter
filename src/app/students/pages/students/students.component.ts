import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {Student} from "../../model/student";
import {StudentsService} from "../../services/students.service";
import {NgForm} from "@angular/forms";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import * as _ from "lodash";
@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit, AfterViewInit {

  studentData: Student;
  dataSource: MatTableDataSource<any>;
  displayedColumns: string[] = ['id', 'name', 'age', 'address', 'actions']

  @ViewChild('studentForm', {static: false})
  studentForm!: NgForm;

  @ViewChild(MatPaginator, {static: true})
  paginator!: MatPaginator;

  @ViewChild(MatSort)
  sort!: MatSort;

  isEditMode = false;

  constructor(private studentsService: StudentsService) {
    this.studentData = {} as Student;
    this.dataSource = new MatTableDataSource<any>();
  }

  ngOnInit(): void {
    this.getAllStudents();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  getAllStudents() {
    this.studentsService.getAll().subscribe((response: any) => {
      this.dataSource.data = response;
    });
  }

  editItem(element: Student) {
    this.studentData = _.cloneDeep(element);
    this.isEditMode = true;
  }

  cancelEdit() {
    this.isEditMode = false;
    this.studentForm.resetForm();
  }

  deleteItem(id: number) {
    this.studentsService.delete(id).subscribe(() => {
      this.dataSource.data = this.dataSource.data.filter((o: Student) => {
        return o.id !== id ? o : false;
      });
    });
    console.log(this.dataSource.data);
  }

  addStudent() {
    this.studentData.id = 0;
    this.studentsService.create(this.studentData).subscribe((response: any) => {
      this.dataSource.data.push({...response});
      this.dataSource.data = this.dataSource.data.map((o: any) => { return o; });
    });
  }

  updateStudent() {
    this.studentsService.update(this.studentData.id, this.studentData).subscribe((response: any) => {
      this.dataSource.data = this.dataSource.data.map((o: Student) => {
        if (o.id == response.id) {
          o = response;
        }
        return o;
      });
    });

  }
  onSubmit() {
    if (this.studentForm.form.valid) {
      console.log('valid');
      if (this.isEditMode) {
        console.log('about to update');
        this.updateStudent();
      } else {
        console.log('about to create');
        this.addStudent();
      }
      this.cancelEdit();
    } else {
      console.log('Invalid data');
    }
  }
}
