import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShot } from 'app/shared/model/shot.model';

@Component({
    selector: 'jhi-shot-detail',
    templateUrl: './shot-detail.component.html'
})
export class ShotDetailComponent implements OnInit {
    shot: IShot;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ shot }) => {
            this.shot = shot;
        });
    }

    previousState() {
        window.history.back();
    }
}
