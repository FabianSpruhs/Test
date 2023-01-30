import {Employee} from "@/interfaces/Employee";

export interface WorkingHour {
    id: number;
    employee:Employee;
    workingDay:string;
    workingHours:number;
    workingHourStatus:string;
}