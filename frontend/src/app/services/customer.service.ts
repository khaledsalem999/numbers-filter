import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Customer} from '../models/customer';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  backendUrl = `${environment.backendUrl}`

  constructor(private http: HttpClient) { }

  getCustomers(stateFilter: string,countryFilter: string,page: string,size: string): Observable<Customer[]>{
    let params = new HttpParams();
    if(stateFilter != null){
      params = params.append('stateFilter', stateFilter)
    }
    if(countryFilter != null){
      params = params.append('countryFilter', countryFilter)
    }
    params = params.append('page', page)
    params = params.append('size', size)
    return this.http.get<Customer[]>(`${this.backendUrl}/customers`,{params: params})
  }

}
