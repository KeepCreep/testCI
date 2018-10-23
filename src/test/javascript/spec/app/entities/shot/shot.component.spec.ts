/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestTestModule } from '../../../test.module';
import { ShotComponent } from 'app/entities/shot/shot.component';
import { ShotService } from 'app/entities/shot/shot.service';
import { Shot } from 'app/shared/model/shot.model';

describe('Component Tests', () => {
    describe('Shot Management Component', () => {
        let comp: ShotComponent;
        let fixture: ComponentFixture<ShotComponent>;
        let service: ShotService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [ShotComponent],
                providers: []
            })
                .overrideTemplate(ShotComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ShotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ShotService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Shot(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.shots[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
