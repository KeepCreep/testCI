/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestTestModule } from '../../../test.module';
import { EaterDetailComponent } from 'app/entities/eater/eater-detail.component';
import { Eater } from 'app/shared/model/eater.model';

describe('Component Tests', () => {
    describe('Eater Management Detail Component', () => {
        let comp: EaterDetailComponent;
        let fixture: ComponentFixture<EaterDetailComponent>;
        const route = ({ data: of({ eater: new Eater(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [EaterDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EaterDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EaterDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eater).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
