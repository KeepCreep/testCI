/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestTestModule } from '../../../test.module';
import { RestaurantComponent } from 'app/entities/restaurant/restaurant.component';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';
import { Restaurant } from 'app/shared/model/restaurant.model';

describe('Component Tests', () => {
    describe('Restaurant Management Component', () => {
        let comp: RestaurantComponent;
        let fixture: ComponentFixture<RestaurantComponent>;
        let service: RestaurantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AppTestTestModule],
                declarations: [RestaurantComponent],
                providers: []
            })
                .overrideTemplate(RestaurantComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RestaurantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RestaurantService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Restaurant(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.restaurants[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
