import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEater } from 'app/shared/model/eater.model';

@Component({
    selector: 'jhi-eater-detail',
    templateUrl: './eater-detail.component.html'
})
export class EaterDetailComponent implements OnInit {
    eater: IEater;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ eater }) => {
            this.eater = eater;
        });
    }

    previousState() {
        window.history.back();
    }
}
