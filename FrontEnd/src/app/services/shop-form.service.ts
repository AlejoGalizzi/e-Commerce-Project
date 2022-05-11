import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { Country } from '../common/country';
import { State } from '../common/state';

@Injectable({
  providedIn: 'root'
})
export class ShopFormService {

  private countryUrl = "http://localhost:8080/api/countries";
  private stateUrl = "http://localhost:8080/api/states";

  constructor(private httpClient: HttpClient) { }

  getCreditCardMonths(startMonth: number) : Observable<number[]> {
    
    let data: number[] = [];
    
    for (let month = startMonth; month <= 12; month++) {
      data.push(month);
    }

    return of(data);
  }

  getCreditCardYears() : Observable<number[]> {
    
    let data: number[] = [];

    const currentYear: number = new Date().getFullYear();
    const endYear: number = currentYear + 10;

    for (let year = currentYear; year < endYear; year++) {
      data.push(year);
    }

    return of(data);
  }

  getCountryList(): Observable<Country[]> {
    return this.httpClient.get<GetResponseCountry>(this.countryUrl).pipe(
      map(response => response._embedded.countries)
    );
  }

  getStateList(countryCode: string): Observable<State[]>{
    const url = `${this.stateUrl}/search/findByCountryCode?code=${countryCode}`;

    return this.httpClient.get<GetResponseState>(url).pipe(
      map(response => response._embedded.states)
    );
  }
}

interface GetResponseState {
  _embedded: {
    states: State[];
  }
}

interface GetResponseCountry {
  _embedded: {
    countries: Country[];
  }
}
