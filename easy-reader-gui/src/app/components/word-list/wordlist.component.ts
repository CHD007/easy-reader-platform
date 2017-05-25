import {Component, OnInit} from '@angular/core';
import {DataService} from '../../services/data.service';
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';
import {Observable} from "rxjs/Observable";
import {Status, Word} from "../../shared/entity/word";

@Component({
  selector: 'word-list',
  templateUrl: 'wordlist.component.html',
  styleUrls: ['wordlist.component.scss']
})
export class WordListComponent implements OnInit {

  private wordList: Observable<Array<Word>>;


  constructor(private service: DataService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.wordList = this.route.params.switchMap((params: Params) => this.service.getBookWords(params['id'], 1));
  }


  changeStatus(event, word: Word) {
    let value: string = event.target.value;
    if (word.status.toString() !== value) {
      word.status = Status[Status[value]];
      this.service.changeWordStatus(word.id,word.status).subscribe();
    }

  }


}


