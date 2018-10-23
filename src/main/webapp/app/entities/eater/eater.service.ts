import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEater } from 'app/shared/model/eater.model';

type EntityResponseType = HttpResponse<IEater>;
type EntityArrayResponseType = HttpResponse<IEater[]>;

@Injectable({ providedIn: 'root' })
export class EaterService {
    private resourceUrl = SERVER_API_URL + 'api/eaters';

    constructor(private http: HttpClient) {}

    create(eater: IEater): Observable<EntityResponseType> {
        return this.http.post<IEater>(this.resourceUrl, eater, { observe: 'response' });
    }

    update(eater: IEater): Observable<EntityResponseType> {
        return this.http.put<IEater>(this.resourceUrl, eater, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEater>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEater[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
