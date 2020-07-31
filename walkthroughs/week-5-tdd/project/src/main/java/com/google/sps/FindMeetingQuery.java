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
    List<Event> takenTimes = getTakenTimes(events, attendees);
    takenTimes = sortTimes(takenTimes);
 
    //find open times
    int eventStart = TimeRange.START_OF_DAY;
    
    for (Event event : takenTimes) {
 
      if (eventStart + duration <= event.getWhen().start()) {
        meetingTimes.add(TimeRange.fromStartEnd(eventStart, event.getWhen().start(), false));
 
        eventStart = event.getWhen().end();

      }
        //in case of nested events
        else if (!(eventStart > event.getWhen().end())) {
            eventStart = event.getWhen().end();
      }
    }
 
    //after unavailable meetings
    int endOfDay = TimeRange.END_OF_DAY - eventStart;
    if (endOfDay >= duration) {
      meetingTimes.add(TimeRange.fromStartEnd(eventStart, TimeRange.END_OF_DAY, true));
    }

    //Get optional attendees taken times
    List<Event> optionalTakenTimes = getTakenTimes(events, optionalAttendees);
    takenTimes.addAll(optionalTakenTimes);
    takenTimes = sortTimes(takenTimes);

    //repeat for optional
    if (!optionalAttendees.isEmpty()){
        eventStart = TimeRange.START_OF_DAY;
        for (Event event : takenTimes) {
            if (eventStart + duration <= event.getWhen().start()) {
                optionalTimes.add(TimeRange.fromStartEnd(eventStart, event.getWhen().start(), false));
                eventStart = event.getWhen().end();
            }
                //in case of nested events
            else if (!(eventStart > event.getWhen().end())) {
                eventStart = event.getWhen().end();
            }
        }

        endOfDay = TimeRange.END_OF_DAY - eventStart;
        if (endOfDay >= duration) {
            optionalTimes.add(TimeRange.fromStartEnd(eventStart, TimeRange.END_OF_DAY, true));
        }

        if (!optionalTimes.isEmpty()){
        return optionalTimes;
        }
    }

    return meetingTimes;
  }
 
    //unavailable times
  private List<Event> getTakenTimes(Collection<Event> events, Collection<String> attendees) {
    List<Event> taken = new ArrayList<>();
    for (Event event : events) {
      for (String attendee : attendees) {
        if (event.getAttendees().contains(attendee)) {
          taken.add(event);
        }
      }
    }
    return taken;
  }
 
  private List<Event> sortTimes(List<Event> events) {
    Comparator<Event> compareByStartTime =
        (Event evt1, Event evt2) ->
            TimeRange.ORDER_BY_START.compare(evt1.getWhen(), evt2.getWhen());
    Collections.sort(events, compareByStartTime);
    return events;
  }
 
 
}