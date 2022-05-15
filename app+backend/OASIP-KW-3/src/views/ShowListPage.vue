<script setup>
import { onBeforeMount, ref, onBeforeUpdate } from "@vue/runtime-core";
import ShowList from "../components/ShowList.vue";
import Navbar from "../components/Navbar.vue";
import { useRoute } from "vue-router";
const route = useRoute();
const eventLists = ref();
const page = ref(0);
const numPage = ref();
const getLink = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/category/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
};

onBeforeMount(async () => {
  getLink();
});

function paging(index , filter) {
  page.value = index;

  if (filter == 3) {
    getLink();
  }
  if (filter == 2) {
    getLinkFuture();
  }
  if (filter == 1) {
    getLinkPast();
  }
}
//http://localhost:8080/api/events/category/1?pageSize=4

const getLinkPast = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/category/past/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
  }
};

const getLinkFuture = async () => {
  const res = await fetch(
    `${import.meta.env.VITE_APP_TITLE}/api/events/category/future/${
      route.query.categoryId
    }?page=${page.value}&pageSize=8`
  );
  if (res.status === 200) {
    eventLists.value = await res.json();
    numPage.value = Math.ceil(eventLists.value.totalElements / 8);
    console.log(eventLists.value);
  }
};

function pastFilter() {
  getLinkPast();
}

function futureFilter() {
  getLinkFuture();
}

function allFilter() {
  getLink();
}
</script>

<template>
  <div>
    <Navbar />

    <ShowList
      :eventLists="eventLists.content"
      colNum="grid-cols-4"
      :name="route.query.eventCategoryName"
      :numPage="numPage"
      @paging="paging"
       @pastFilter="pastFilter"
      @futureFilter="futureFilter"
      @allFilter="allFilter"
    />
    {{ page }}
  </div>
</template>

<style></style>
