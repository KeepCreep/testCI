import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IShot } from 'app/shared/model/shot.model';

type EntityResponseType = HttpResponse<IShot>;
type EntityArrayResponseType = HttpResponse<IShot[]>;

@Injectable({ providedIn: 'root' })
export class ShotService {
    private resourceUrl = SERVER_API_URL + 'api/shots';

    constructor(private http: HttpClient) {}

    create(shot: IShot): Observable<EntityResponseType> {
        return this.http.post<IShot>(this.resourceUrl, shot, { observe: 'response' });
    }

    update(shot: IShot): Observable<EntityResponseType> {
        return this.http.put<IShot>(this.resourceUrl, shot, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IShot>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IShot[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
