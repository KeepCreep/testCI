/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestTestModule } from '../../../test.module';
import { ShotDetailComponent } from 'app/entities/shot/shot-detail.component';
import { Shot } from 'app/shared/model/shot.model';

describe('Component Tests', () => {
    describe('Shot Management Detail Component', () => {
        let comp: ShotDetailComponent;
        let fixture: ComponentFixture<ShotDetailComponent>;
        const route = ({ data: of({ shot: new Shot(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [ShotDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ShotDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ShotDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.shot).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
