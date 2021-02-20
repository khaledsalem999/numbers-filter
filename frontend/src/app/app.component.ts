import {Component, OnInit} from '@angular/core';
import {CustomerService} from './services/customer.service';
import {share} from 'rxjs/operators';
import {Customer} from './models/customer';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

  title = 'frontend';
  customers$: Customer[];
  customerQuery: FormGroup;
  countriesQuery = '';
  countries = [];
  page = 0;


  constructor(private customerService:CustomerService,private formBuilder: FormBuilder) {
    this.customerQuery = this.formBuilder.group({
      customerPhoneState: new FormControl(null)
    })
  }

  ngOnInit(): void {
    this.updateList();
  }

  updateList(){
    if(this.countriesQuery == ''){
      this.customerService.getCustomers(this.customerQuery.get("customerPhoneState").value,
        null,this.page.toString(),"10").pipe(share()).subscribe(
        (customers) => this.customers$ = customers
      )
    }else{
      this.customerService.getCustomers(this.customerQuery.get("customerPhoneState").value,
        this.countriesQuery,this.page.toString(),"10").pipe(share()).subscribe(
        (customers) => this.customers$ = customers
      )
    }

  }

  changesState(){
    this.page = 0;
    this.updateList()
  }

  formCountriesQueryString(event){
    const checked = event.target.checked;
    const country = event.target.value;
    if (checked) {
      this.countries.push({country_ : country});
      this.countriesQuery = this.countriesQuery.concat(country);
      console.log(this.countriesQuery)
    } else {
      const index = this.countries.findIndex(list => list.country_ == country);
      this.countriesQuery = this.countriesQuery.replace(this.countries[index].country_,'');
      this.countries.splice(index, 1);
    }
    this.page = 0;
    this.updateList()
  }

  incrementPage(){
    if(this.customers$.length<10){
      //do nothing
    }else{
      this.page++
      this.updateList()
    }
  }

  decrementPage(){
    if(this.page==0){
      //do nothing
    }else{
      this.page--
      this.updateList()
    }
  }
}
