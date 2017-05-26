import {Injectable} from '@angular/core';
import {Headers, Http, RequestOptions, ResponseContentType} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {HeadersConstants} from '../shared/headers.constants';
import {Routes} from '../shared/api.routes';
import {Book} from '../shared/entity/book';
import {Status, Word} from '../shared/entity/word';




@Injectable()
export class DataService {

  private headers: Headers = new Headers();

  constructor(private http: Http) {
    this.headers.append(HeadersConstants.CONTENT_TYPE, 'application/x-www-form-urlencoded; chartset=UTF-8');
  }

  /**
   * Gets all books from the server via GET request
   *
   * @returns {Observable<T>}
   */
  public getAllBooks(): Observable<Array<Book>> {
    return Observable.create((observer) => {
      this.http.get(Routes.GET_ALL_BOOKS)
        .catch(error => Observable.throw(error))
        .subscribe(
          result => {
            observer.next(result.json());
          },
          error => {
            observer.error(error);
          }
        );
    }).take(1);
  }


  public exportPDF(id: number, startWord: number, endWord: number) {
    let headers = new Headers({

    });
    let params: URLSearchParams = new URLSearchParams();
    let options = new RequestOptions({headers: headers});
    options.responseType = ResponseContentType.Blob;
    options.headers = headers;
    options.params = <any>params;

    return Observable.create((observer) => {
      this.http.get(Routes.exportPDF(id), options)
        .catch(error => Observable.throw(error))
        .subscribe(
          result => {
            observer.next(result);
          },
          error => {
            observer.error(error);
          }
        );
    }).take(1);
  }

  /**
   * Gets All words by book id
   *
   * @param id - book id
   * @param page - number of page
   */
  public getBookWords(id: string, page = 1): Observable<Array<Word>> {
    return Observable.create((observer) => {
      this.http.get(Routes.getAllWordsByBookId(id, page))
        .catch(error => Observable.throw(error))
        .subscribe(
          result => {
            observer.next(result.json());
          },
          error => {
            observer.error(error);
          }
        );
    }).take(1);
  }


  public changeWordStatus(wordId: number, status: string) {
    return Observable.create((observer) => {
      let params = new URLSearchParams();
      params.append('status', status);
      this.http.post(Routes.updateWordStatus(wordId), params.toString(), {headers: this.headers})
        .catch(error => Observable.throw(error))
        .subscribe(
          result => {
          },
          error => {
            observer.error(error);
          }
        );
    }).take(1);
  }

  /**
   * Gets all books from the server via GET request
   *
   * @returns {Observable<T>}
   */
  public deleteBookById(id: number): Observable<void> {
    return Observable.create((observer) => {
      this.http.delete(`${Routes.DELETE_BOOK_BY_ID}/${id}`)
        .catch(error => Observable.throw(error))
        .subscribe(
          result => {
            console.log(result);
            observer.next();
          },
          error => {
            observer.error(error);
          }
        );
    }).take(1);
  }

  /**
   * Uploads file on server via post request
   *
   * @param fileList - file list
   * @param uploadFileName - name of the file
   * @returns {Observable<T>}
   */
  public uploadFile(fileList: FileList, uploadFileName: string): Observable<void> {
    let file: File = fileList[0];
    let formData: FormData = new FormData();
    formData.append('uploadFile', file, file.name);
    formData.append('fileName', uploadFileName);
    let headers = new Headers();
    headers.append(HeadersConstants.CONTENT_TYPE, HeadersConstants.MULTI_PART);
    headers.append(HeadersConstants.ACCEPT, HeadersConstants.APPLICATION_JSON);
    let options = new RequestOptions(<any>{headers: headers});

    return Observable.create((observer) => {
      this.http.post(Routes.UPLOAD_FILE, formData, options)
        .catch(error => Observable.throw(error))
        .subscribe(
          data => observer.next(data),
          error => console.error(error)
        );
    }).take(1);
  }

}
