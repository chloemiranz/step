// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
 
package com.google.sps;
 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

 
public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    Collection<TimeRange> meetingTimes = new ArrayList<TimeRange>();
    Collection<TimeRange> optionalTimes = new ArrayList<TimeRange>();
    Collection<String> attendees = request.getAttendees();
    Collection<String> optionalAttendees = request.getOptionalAttendees();
    long duration = request.getDuration();
    
    //no attendees
    if (attendees.isEmpty() && optionalAttendees.isEmpty()) {
      meetingTimes.add(
          TimeRange.fromStartEnd(TimeRange.START_OF_DAY, TimeRange.END_OF_DAY, true));
      return meetingTimes;
    }
 
    //Get taken times
    List<TimeRange> takenTimes = getTakenTimes(events, attendees);
 
    //find open times
    int eventStart = TimeRange.START_OF_DAY;
    
    for (TimeRange eventTime : takenTimes) {

      if (eventStart + duration <= eventTime.start()) {
        meetingTimes.add(TimeRange.fromStartEnd(eventStart, eventTime.start(), false));
 
        eventStart = eventTime.end();
      }
      //in case of nested events
      else if (!(eventStart > eventTime.end())) {
        eventStart = eventTime.end();
      }
    }
 
    //after unavailable meetings
    int endOfDay = TimeRange.END_OF_DAY - eventStart;
    if (endOfDay >= duration) {
      meetingTimes.add(TimeRange.fromStartEnd(eventStart, TimeRange.END_OF_DAY, true));
    }

    //Get optional attendees taken times
    List<String> allAttendees = new ArrayList<>();
    allAttendees.addAll(attendees);
    allAttendees.addAll(optionalAttendees);
    List<TimeRange> optionalTakenTimes = getTakenTimes(events, allAttendees);
    
    //repeat for optional
    if (optionalAttendees.isEmpty()) {
      return meetingTimes;
    } 

    eventStart = TimeRange.START_OF_DAY;
    for (TimeRange eventTime : optionalTakenTimes) {
      if (eventStart + duration <= eventTime.start()) {
        optionalTimes.add(TimeRange.fromStartEnd(eventStart, eventTime.start(), false));
        eventStart = eventTime.end();
      }
      //in case of nested events
      else if (!(eventStart > eventTime.end())) {
        eventStart = eventTime.end();
      }
    }

    endOfDay = TimeRange.END_OF_DAY - eventStart;
    if (endOfDay >= duration) {
      optionalTimes.add(TimeRange.fromStartEnd(eventStart, TimeRange.END_OF_DAY, true));
    }

    if (!optionalTimes.isEmpty()) {
      return optionalTimes;
    }

    return meetingTimes;
  }
 
  //unavailable times
  private List<TimeRange> getTakenTimes(Collection<Event> events, Collection<String> attendees) {
    List<TimeRange> taken = new ArrayList<>();
    //sort events
    List<Event> eventsList = new ArrayList(events);
    Comparator<Event> compareByStartTime =
        (Event event1, Event event2) ->
            TimeRange.ORDER_BY_START.compare(event1.getWhen(), event2.getWhen());
    Collections.sort(eventsList, compareByStartTime);

    for (Event event : eventsList) {
      for (String attendee : attendees) {
        if (event.getAttendees().contains(attendee)) {
          taken.add(event.getWhen());
        }
      }
    }
    return taken;
  }

}