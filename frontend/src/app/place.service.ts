import { Injectable } from '@angular/core';
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";
import {RequestFindPlaces} from "./app.component";
import {Observable} from "rxjs";
import {Place} from "./place";

@Injectable({
  providedIn: 'root'
})
export class PlaceService {
  private REQUEST_FIND_PLACES = environment.mainURL + "/api/place";

  constructor(private http:HttpClient) { }

  findPlaces(req: RequestFindPlaces): Observable<Place[]> {
    return this.http.post<Place[]>(this.REQUEST_FIND_PLACES, req);
  }
}
