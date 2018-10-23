import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMeal } from 'app/shared/model/meal.model';

type EntityResponseType = HttpResponse<IMeal>;
type EntityArrayResponseType = HttpResponse<IMeal[]>;

@Injectable({ providedIn: 'root' })
export class MealService {
    private resourceUrl = SERVER_API_URL + 'api/meals';

    constructor(private http: HttpClient) {}

    create(meal: IMeal): Observable<EntityResponseType> {
        return this.http.post<IMeal>(this.resourceUrl, meal, { observe: 'response' });
    }

    update(meal: IMeal): Observable<EntityResponseType> {
        return this.http.put<IMeal>(this.resourceUrl, meal, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMeal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMeal[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
