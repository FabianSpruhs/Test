import {Client} from "@/interfaces/Client";

export interface Project {
    id:number;
    client:Client;
    name:string;
}