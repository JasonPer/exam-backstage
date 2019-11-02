import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDiscountInfo } from 'app/shared/model/discount-info.model';

type EntityResponseType = HttpResponse<IDiscountInfo>;
type EntityArrayResponseType = HttpResponse<IDiscountInfo[]>;

@Injectable({ providedIn: 'root' })
export class DiscountInfoService {
  public resourceUrl = SERVER_API_URL + 'api/discount-infos';

  constructor(protected http: HttpClient) {}

  create(discountInfo: IDiscountInfo): Observable<EntityResponseType> {
    return this.http.post<IDiscountInfo>(this.resourceUrl, discountInfo, { observe: 'response' });
  }

  update(discountInfo: IDiscountInfo): Observable<EntityResponseType> {
    return this.http.put<IDiscountInfo>(this.resourceUrl, discountInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDiscountInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDiscountInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
