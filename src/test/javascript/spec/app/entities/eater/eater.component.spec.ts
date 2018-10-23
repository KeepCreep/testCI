/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestTestModule } from '../../../test.module';
import { EaterComponent } from 'app/entities/eater/eater.component';
import { EaterService } from 'app/entities/eater/eater.service';
import { Eater } from 'app/shared/model/eater.model';

describe('Component Tests', () => {
    describe('Eater Management Component', () => {
        let comp: EaterComponent;
        let fixture: ComponentFixture<EaterComponent>;
        let service: EaterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [EaterComponent],
                providers: []
            })
                .overrideTemplate(EaterComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EaterComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EaterService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Eater(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.eaters[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
