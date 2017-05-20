import {Component} from '@angular/core';
import {Book} from "../../shared/book";
import {Http, RequestOptions} from "@angular/http";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'book-library',
  templateUrl: 'booklibrary.component.html',
  styleUrls: ['booklibrary.component.scss']
})
export class BookLibraryComponent {

  private isUploadFormHidden: boolean = true;
  private isFileSelected: boolean = false;

  private books: Array<Book> = [];

  constructor(private http: Http) {
    this.books.push(new Book("Alice in Wonderland", 2453, 876, 1200));
    this.books.push(new Book("The Story of Eugene Titkov", 2453, 876, 1200));
    this.books.push(new Book("Nothing but truth", 2453, 876, 1200));
  }

  changeUploadFormState() {
    this.isUploadFormHidden = !this.isUploadFormHidden;
  }

  changeUpload(event: any) {
    this.isFileSelected = !this.isFileSelected;
    let fileList: FileList = event.target.files;
    if (fileList.length > 0) {
      let file: File = fileList[0];
      let formData: FormData = new FormData();
      formData.append('uploadFile', file, file.name);
      let headers = new Headers();
      headers.append('Content-Type', 'multipart/form-data');
      headers.append('Accept', 'application/json');
      let options = new RequestOptions(<any>{headers: headers});

      this.http.post("http://localhost:8079", formData, options)
        .map(res => res.json())
        .catch(error => Observable.throw(error))
        .subscribe(
          data => console.log(data),
          error => console.log(error)
        )
    }
  }

}


