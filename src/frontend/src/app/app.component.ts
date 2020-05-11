import { Component } from '@angular/core';
import { NewsApiService } from './news-api.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  articles: Array<any>;
  keys = Object.keys;
  categories = NewsApiService.Category;
  category = NewsApiService.Category.TECHNOLOGY;
  country = NewsApiService.Country.PL;
  totalResults = Number;
  pageSize = 3;
  page = 0;

  constructor(private newsApi: NewsApiService) { }

  ngOnInit() {
    this.getArticles(this.country, this.category, this.pageSize, this.page);
  }

  pageChanged(page) {
    this.getArticles(this.country, this.category, this.pageSize, page);
  }

  getArticles(country: NewsApiService.Country, category: NewsApiService.Category, pageSize: Number, page: Number) {
    this.newsApi.getNews(country, category, pageSize, page).subscribe(data => {
      this.country = country;
      this.category = category;
      this.articles = data['articles'];
      this.totalResults = data['totalResults'];
    });
  }
}
