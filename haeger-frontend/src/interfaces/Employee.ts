import {Address} from "@/interfaces/Address";

export interface Employee {
    id:number;
    firstName:string;
    lastName:string;
    address:Address;
    mail:string;
    telephoneNumber:string;
    status:string;
    role:string;
    scheduledVacationDays:number
}