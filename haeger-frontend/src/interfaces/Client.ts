import {Address} from "@/interfaces/Address";
import {Employee} from "@/interfaces/Employee";

export interface Client {
    id:number;
    name:string;
    address:Address;
    contactEmployee?:Employee;
}