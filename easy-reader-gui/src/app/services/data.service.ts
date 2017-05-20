import {Injectable} from '@angular/core';
import {Http, RequestOptions} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {HeadersConstants} from '../shared/headers.constants';
import {Routes} from '../shared/api.routes';

@Injectable()
export class DataService {

  constructor(private http: Http) {
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
