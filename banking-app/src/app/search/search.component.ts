import {Component, OnInit} from '@angular/core';
import {AsyncPipe} from "@angular/common";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {MatButton} from "@angular/material/button";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {Observable} from "rxjs";
import {Country} from "../map/map.component";
import {map, startWith} from "rxjs/operators";

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [
    AsyncPipe,
    FormsModule,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatButton,
    MatFormField,
    MatInput,
    MatLabel,
    MatOption,
    ReactiveFormsModule
  ],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent implements OnInit 
{
  searchString = '';
  myControl = new FormControl<string | Country>('');
  options: Country[] = [{name: 'Mary'}, {name: 'Shelley'}, {name: 'Igor'}];
  filteredOptions: Observable<Country[]> | undefined;
  
  ngOnInit(): void {
    this.filteredOptions = this.myControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          const name = typeof value === 'string' ? value : value?.name;
          return name ? this._filter(name as string) : this.options.slice();
        }),
    );
  }
  
  displayFn(country: Country): string {
    return country && country.name ? country.name : '';
  }

  private _filter(name: string): Country[] {
    const filterValue = name.toLowerCase();
    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }
  
  searchWorks() {
    alert(this.searchString);
  }
}
