<template>
  <v-sheet class="pa-12" rounded>
    <v-card class="mx-auto" max-width="1200" title="Meine Projekte">
      <v-card-text>
        <ProjectTable :projects="myProjects"/>
      </v-card-text>
        <div class="text-center">
          <v-pagination class="text-center"
                        v-model="page"
                        :length="totalPages"
                        :total-visible="7"
                        @click="getPage()"
          ></v-pagination>
        </div>
    </v-card>
  </v-sheet>
</template>

<script lang="ts">
import ProjectTable from "@/components/ProjectTable.vue";
import {defineComponent} from "vue";

export default defineComponent({
  name: "MyProjectView",
  components: {ProjectTable},

})
</script>

<script setup lang="ts">
import {onBeforeMount, Ref, ref} from "vue";
import {HTTP} from "@/services/httpCommon";
import {Project} from "@/interfaces/Project";
import {useRoute} from "vue-router";

const myProjects: Ref<Project[]> = ref([])
const page = ref(1)
const totalPages = ref(1)
const route = useRoute()

function getPage(): void {
  HTTP.get(`/api/project/employee/${route.params.id}?page=${page.value - 1}&size=10`)
      .then(res => {
        myProjects.value = res.data
        totalPages.value = res.data.totalPages
      });
}

onBeforeMount(() => {
  HTTP.get(`/api/project/employee/${route.params.id}?page=0&size=10`)
      .then(res => {
        myProjects.value = res.data
        totalPages.value = res.data.totalPages
      });
})
</script>