import {Component, OnInit} from '@angular/core';
import {Book} from '../../shared/entity/book';
import {DataService} from '../../services/data.service';
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'book-library',
  templateUrl: 'booklibrary.component.html',
  styleUrls: ['booklibrary.component.scss']
})
export class BookLibraryComponent implements OnInit {
  uploadFileName: string;
  labelStates: string[] = ['Choose file', 'File selected'];

  private bookList: Observable<Array<Book>>;

  private isUploadFormHidden: boolean = true;
  private isFileSelected: boolean = false;
  private fileList: FileList;

  constructor(private service: DataService) {
  }

  ngOnInit(): void {
    this.getAllBooks();
  }

  changeUploadFormState() {
    this.isUploadFormHidden = !this.isUploadFormHidden;
  }

  /**
   * Prepares file and view for uploading
   *
   * @param event - event which is passed from input element
   */
  changeUpload(event: any) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.isFileSelected = true;
      this.fileList = fileList;
      this.uploadFileName = fileList[0].name;
    }
  }

  /**
   * Uploads file on server
   */
  performUpload() {
    this.service.uploadFile(this.fileList, this.uploadFileName).subscribe(
      (result) => {
        this.fileList = null;
        this.isFileSelected = false;
        this.isUploadFormHidden = true;
        this.uploadFileName = '';
        this.getAllBooks();
      }
    );
  }

  /**
   * Deletes book by its id
   *
   * @param id - id of the book
   */
  deleteBookById(id: number) {
    this.service.deleteBookById(id).subscribe(
      (result) => this.getAllBooks());
  }

  /**
   * Gets all book from the server
   */
  private getAllBooks(): void {
    this.bookList = this.service.getAllBooks();
  }


}


