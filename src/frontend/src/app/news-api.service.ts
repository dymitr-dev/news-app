import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class NewsApiService {
  apiUrl = 'http://localhost/api';

  constructor(private http: HttpClient) { }

  getNews(country: NewsApiService.Country, category: NewsApiService.Category, pageSize: Number, page: Number) {
    return this.http.get(this.apiUrl + '/news/' + country.toString() + '/' + category.toString() + '?size=' + pageSize + '&page=' + page);
  }
}

export namespace NewsApiService {
  export enum Category {
    BUSINESS = 'business',
    ENTERTAINMENT = 'entertainment',
    GENERAL = 'general',
    HEALTH = 'health',
    SCIENCE = 'science',
    SPORTS = 'sports',
    TECHNOLOGY = 'technology'
  }

  export enum Country {
    AE = 'ae', AU = 'au', AR = 'ar', BE = 'be', BG = 'bg',
    BR = 'br', CA = 'ca', CH = 'ch', CN = 'cn', CO = 'co',
    CU = 'cu', CZ = 'cz', AT = 'at', DE = 'de', EG = 'eg',
    FR = 'fr', GB = 'gb', GR = 'gr', HK = 'hk', HU = 'hu',
    ID = 'id', IE = 'ie', IL = 'il', IN = 'in', IT = 'it',
    JP = 'jp', KR = 'kr', LT = 'lt', LV = 'lv', MA = 'ma',
    MX = 'mx', MY = 'my', NG = 'ng', NL = 'nl', NO = 'no',
    NZ = 'nz', PH = 'ph', PL = 'pl', PT = 'pt', RO = 'ro',
    RU = 'ru', RS = 'rs', SA = 'sa', SE = 'se', SG = 'sg',
    SI = 'si', SK = 'sk', TH = 'th', TR = 'tr', TW = 'tw',
    UA = 'ua', US = 'us', VE = 've', ZA = 'za'
  }
}
