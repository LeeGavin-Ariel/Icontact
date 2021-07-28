<template>
  <div style="display: flex">

    
  <!-- 사이드바 -->
    <v-card height="100vh">
    <v-navigation-drawer
      permanent
    >
      <v-list dense>
        <v-list-item
          v-for="item in items"
          :key="item.title"
        >
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-card>
  

  <!-- 전체 조회 -->
  <v-card
    class="mx-auto"
    max-width="500"
  >
    <v-list two-line>
      <v-list-item-group
        v-model="selected"
        active-class="pink--text"
        multiple
      >
        <template v-for="(note, index) in notes">
          <v-list-item :key="note.title">
            <template v-slot:default="{ active }">
              <v-list-item-content>
                <v-list-item-title v-text="note.title"></v-list-item-title>

                <v-list-item-subtitle
                  class="text--primary"
                  v-text="note.headline"
                ></v-list-item-subtitle>

                <v-list-item-subtitle v-text="note.subtitle"></v-list-item-subtitle>
              </v-list-item-content>

              <v-list-item-action>
                <v-list-item-action-text v-text="note.action"></v-list-item-action-text>

                <v-icon
                  v-if="!active"
                  color="grey lighten-1"
                >
                  mdi-star-outline
                </v-icon>

                <v-icon
                  v-else
                  color="yellow darken-3"
                >
                  mdi-star
                </v-icon>
              </v-list-item-action>
            </template>
          </v-list-item>

          <v-divider
            v-if="index < notes.length - 1"
            :key="index"
          ></v-divider>
        </template>
      </v-list-item-group>
    </v-list>
  </v-card>

  <v-col>
    <v-sheet
      min-height="100vh"
      rounded="lg"
    >
      h2h2h2h
    </v-sheet>
  </v-col>

  
  </div>
</template>

<script>
export default {
  name: "Notebook",
  data () {
    return {
      items: [
        { title: 'Home', icon: 'mdi-home-city' },
        { title: 'My Account', icon: 'mdi-account' },
        { title: 'Users', icon: 'mdi-account-group-outline' },
      ],
      selected: [2],
      notes: [
        {
          action: '15 min',
          headline: 'Brunch this weekend?',
          subtitle: `I'll be in your neighborhood doing errands this weekend. Do you want to hang out?`,
          title: 'Ali Connors',
        },
        {
          action: '2 hr',
          headline: 'Summer BBQ',
          subtitle: `Wish I could come, but I'm out of town this weekend.`,
          title: 'me, Scrott, Jennifer',
        },
        {
          action: '6 hr',
          headline: 'Oui oui',
          subtitle: 'Do you have Paris recommendations? Have you ever been?',
          title: 'Sandra Adams',
        },
        {
          action: '12 hr',
          headline: 'Birthday gift',
          subtitle: 'Have any ideas about what we should get Heidi for her birthday?',
          title: 'Trevor Hansen',
        },
        {
          action: '18hr',
          headline: 'Recipe to try',
          subtitle: 'We should eat this: Grate, Squash, Corn, and tomatillo Tacos.',
          title: 'Britta Holt',
        },
      ],
    }
  }
}
</script>

<style>

</style>