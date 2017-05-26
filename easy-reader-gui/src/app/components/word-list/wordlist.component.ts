import {Component, OnInit} from '@angular/core';
import {DataService} from '../../services/data.service';
import {ActivatedRoute, Params} from '@angular/router';
import {Location} from '@angular/common';
import {Observable} from 'rxjs/Observable';
import {Status, Word} from '../../shared/entity/word';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'word-list',
  templateUrl: 'wordlist.component.html',
  styleUrls: ['wordlist.component.scss']
})
export class WordListComponent implements OnInit {

  isExportActive = false;
  isViewConfigActive = false;

  currentPage = 1;

  startWord = 1;
  endWord = 100;

  private wordList: Observable<Array<Word>>;


  constructor(private service: DataService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.wordList = this.route.params.switchMap((params: Params) => this.service.getBookWords(params['id'], this.currentPage));
  }


  changeStatus(event, word: Word) {
    let value: string = event.target.value;
    if (word.status.toString() !== value) {
      word.status = Status[Status[value]];
      this.service.changeWordStatus(word.id, word.status).subscribe();
    }
  }


  nextPage(pageIncr: number) {
    this.currentPage += pageIncr;
    this.currentPage = this.currentPage > 0 ? this.currentPage : 1;
    this.wordList = this.route.params.switchMap((params: Params) => this.service.getBookWords(params['id'], this.currentPage));
  }

  changeExportMenu() {
    this.isExportActive = !this.isExportActive;
  }

  changeViewConfigView() {
    this.isViewConfigActive = !this.isViewConfigActive;
  }

  exportPDF() {
    this.route.params.switchMap((params: Params) => this.service.exportPDF(params['id'], this.startWord, this.endWord))
      .subscribe(
        result => {
          this.downloadFile(<any>result);
        }
      );
  }

  private downloadFile(response: Response) {
    console.log(response);
    let blob = new Blob([response.blob()], {type: 'application/pdf'});
    let filename = 'mypdf.pdf';
    FileSaver.saveAs(blob, filename);
  }


}


