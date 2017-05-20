import {Component} from '@angular/core';
import {Book} from '../../shared/book';
import {DataService} from '../../services/data.service';

@Component({
  selector: 'book-library',
  templateUrl: 'booklibrary.component.html',
  styleUrls: ['booklibrary.component.scss']
})
export class BookLibraryComponent {
  uploadFileName: string;
  labelStates: string[] = ['Choose file', 'File selected'];

  private isUploadFormHidden: boolean = true;
  private isFileSelected: boolean = false;
  private fileList: FileList;

  private books: Array<Book> = [];

  constructor(private service: DataService) {
    this.books.push(new Book('Alice in Wonderland', 2453, 876, 1200));
    this.books.push(new Book('The Story of Eugene Titkov', 2453, 876, 1200));
    this.books.push(new Book('Nothing but truth', 2453, 876, 1200));
  }

  changeUploadFormState() {
    this.isUploadFormHidden = !this.isUploadFormHidden;
  }

  changeUpload(event: any) {
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      this.isFileSelected = true;
      this.fileList = fileList;
      this.uploadFileName = fileList[0].name;
    }
  }

  performUpload() {
    this.service.uploadFile(this.fileList, this.uploadFileName).subscribe(
      (result) => {
        console.log(result);
        this.fileList = null;
        this.isFileSelected = false;
        this.isUploadFormHidden = true;
        this.uploadFileName = '';
      }
    );
  }

}


