import {Employee} from "@/interfaces/Employee";

export interface SickNote {
    beginDate:string;
    endDate:string;
    id:number;
    employee:Employee
}