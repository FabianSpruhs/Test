<template>
  <div>
    <v-autocomplete
        :label="props.label"
        v-model="clientName"
        :items="clientsView">
    </v-autocomplete>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";

export default defineComponent({
  name: "ClientAutoComplete",
})
</script>

<script setup lang="ts">
import {defineEmits, defineProps, onBeforeMount, Ref, ref, watch} from "vue";
import {Client} from "@/interfaces/Client";
import {HTTP} from "@/services/httpCommon";
import {AutoCompleteResponse} from "@/interfaces/AutoCompleteResponse";

const clients: Ref<Client[]> = ref([])
const clientName = ref('')
const clientsView: Ref<string[]> = ref([])

const props = defineProps<{
  label: string
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', clientResponse:AutoCompleteResponse): void
}>()

onBeforeMount(() => {
  HTTP.get("/api/client/accounting/all")
      .then(res => { clients.value = res.data.content });
})

watch(clients, () => {
  clientsView.value = []
  clients.value.forEach(client => clientsView.value.push(client.name))
})

function createClientResponse ():AutoCompleteResponse {
  let clientID = 0
  clients.value.forEach((client) => {
    if (client.name === clientName.value) {
      clientID = client.id;
    }
  });
  return {id: clientID, name: clientName.value}
}

watch(clientName, () => {
  emit('update:modelValue', createClientResponse())
})

</script>