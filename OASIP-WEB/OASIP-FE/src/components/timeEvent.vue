<script setup>
import { ref, onBeforeMount, onBeforeUpdate, computed } from "vue";
import { useRoute } from "vue-router";
import { useRouter } from "vue-router";
import RoundButton from "../components/RoundButton.vue";

// const myRouter = useRoute();
const router = useRouter();
const props = defineProps({
    cate: {
        type: Object,
    },
    role: {
        type: String
    }
});


onBeforeUpdate(async () => {

});

const UserRole = ref()

onBeforeMount(async () => {
    getLinkFuture()
    UserRole.value = localStorage.getItem('UserRole');
});


const style = "flex flex-wrap   justify-content-center grid grid-cols-1";

// filter
const timeCheck = ref({ content: "" })

const getLinkFuture = async () => {
    //RefreshToken()
    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/datetime/future`,
        {

            method: 'get',
            headers: {

                'Content-Type': 'application/json',

            }
        });
    if (res.status === 200) {
        timeCheck.value = await res.json();
        console.log(timeCheck.value)
        addAlldate()

    }

};
const getLinkPast = async () => {
    //RefreshToken()
    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/datetime/past`,
        {

            method: 'get',
            headers: {

                'Content-Type': 'application/json',

            }
        });
    if (res.status === 200) {
        timeCheck.value = await res.json();
        console.log(timeCheck.value)
        addAlldate()

    }

};
const getLinkAll = async () => {
    //RefreshToken()
    const res = await fetch(`${import.meta.env.VITE_APP_TITLE}/api/events/datetime`,
        {

            method: 'get',
            headers: {

                'Content-Type': 'application/json',

            }
        });
    if (res.status === 200) {
        timeCheck.value = await res.json();
        console.log(timeCheck.value)
        addAlldate()

    }

};


const alldate = ref([])

function addAlldate() {
    alldate.value=[]
    console.log(props.role)
    if (props.role != "Guest") {
        timeCheck.value.forEach(element => {
            if (element.eventCategory == props.cate.categoryName) {
                alldate.value.push(element.date)

            }


        });

    }

    if (props.role == "Guest") {
        timeCheck.value.forEach(element => {

            alldate.value.push(element.date)

            console.log(alldate.value)


        });

    }

    let uniqueChars = [...new Set(alldate.value)];

    alldate.value = uniqueChars

}

const filterStatus =ref("upcoming")
</script>

<template>
    <div>
        <div v-if="props.role == `Guest`" class="flex justify-center text-sm mb-2"> filter status : <span class="text-green-500 ml-1">{{ filterStatus }}</span></div>
                <div v-if="props.role == `Guest`" class="flex justify-center">
            <div class="text-sm bg-blue-300 drop-shadow-md hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full mx-1" @click="getLinkAll() , filterStatus =`all event` " >all event</div>
            <div class="text-sm bg-rose-300 drop-shadow-md hover:bg-orange-700 text-white font-bold py-2 px-4 rounded-full mx-1" @click="getLinkFuture() , filterStatus =`upcoming`" >upcoming</div>
            <div class="text-sm bg-rose-300 drop-shadow-md hover:bg-orange-700 text-white font-bold py-2 px-4 rounded-full mx-1" @click="getLinkPast(), filterStatus =`past` " >past</div>
        </div>
         
        <div class="flex justify-center  bg-gray-100  " v-if="props.role != `Guest`">
            <div class="bg-white rounded-lg w-full p-4 shadow">
                

                <div v-for="date in alldate">
                    <span
                        class="text-gray-900 relative inline-block date uppercase font-medium tracking-widest">{{ date }}</span>

                    <div v-for="time in timeCheck">
                        <div class="flex mb-2" v-if="time.date == date & cate.categoryName == time.eventCategory">
                            <div class="w-2/12">
                                <span class="text-sm text-gray-600 block"> {{ new
                                        Date(time.eventStartTime).toLocaleTimeString("th-TH")
                                }}</span>
                                <span class="text-sm text-gray-600 block">{{ new
                                        Date(time.endTime).toLocaleTimeString("th-TH")
                                }}</span>
                            </div>
                            <div class="w-1/12">
                                <span class="bg-blue-400 h-2 w-2 rounded-full block mt-2"></span>
                            </div>
                            <div class="w-9/12">
                                <span class="text-sm font-semibold block">{{ time.eventCategory }}</span>

                                <span class="text-sm">{{ time.eventDuration }} min</span>
                            </div>
                        </div>

                    </div>

                </div>



            </div>
        </div>
        
        <div class="flex justify-center">
            <div class="flex justify-center   pt-16 px-8 md:p-8 md:w-2/4  w-full   " v-if="props.role == `Guest`">
                <div class="bg-white rounded-lg w-full p-8 shadow">
                    
                    <div class="md:ml-16 ml-12">
                        <div v-if="alldate.length==0" class="text-center"> event empty </div>
                    <div v-for="date in alldate" class="">
                        <span
                            class="text-gray-900 relative inline-block date uppercase font-medium tracking-widest">{{ date }}</span>

                        <div v-for="time in timeCheck">

                            <div class="flex mb-2 mt-2" v-if="time.date == date">
                                <div class="w-2/12 mt-0.5">
                                    <span class="text-sm text-gray-600 block"> {{ new
                                            Date(time.eventStartTime).toLocaleTimeString("th-TH")
                                    }}</span>
                                    <span class="text-sm text-gray-600 block">{{ new
                                            Date(time.endTime).toLocaleTimeString("th-TH")
                                    }}</span>
                                </div>
                                <div class="w-1/12">
                                </div>
                                <div class="w-9/12">
                                    <span class="text-sm font-semibold block text-red-400 drop-shadow-md">{{ time.eventCategory }}</span>
                                          
                                    <span class="text-sm">{{ time.eventDuration }} min</span>
                                </div>
                            </div>

                        </div>

                    </div>

</div>

                </div>
            </div>
        </div>

    </div>

</template>

<style>
body {
    font-size: 20px;
}

.date:after {
    content: "";
    position: absolute;
    border-top: 1px solid #e2e8f0;
    top: 12px;
    width: 150px;
}

.date:after {
    margin-left: 15px;
}
</style>
