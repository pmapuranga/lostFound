/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.example.lostapp.controller;

import com.example.lostapp.exception.ResourceNotFoundException;
import com.example.lostapp.model.Record;
import com.example.lostapp.repository.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin

public class RecordController {

    @Autowired
    private RecordsRepository recordsRepository;

    /**
     * Get all records list.
     *
     * @return the list
     */
    @GetMapping("/records")
    public List<Record> getAllRecords() {
        return recordsRepository.findAll();
    }

    /**
     * Gets records by id.
     *
     * @param recordId the record id
     * @return the records by id
     * @throws ResourceNotFoundException the resource not found exception
     */
    @GetMapping("/records/{id}")
    public ResponseEntity<Record> getRecordsById(@PathVariable(value = "id") Long recordId)
            throws ResourceNotFoundException {
        Record record =
                recordsRepository
                        .findById(recordId)
                        .orElseThrow(() -> new ResourceNotFoundException("Record not found on :: " + recordId));
        return ResponseEntity.ok().body(record);
    }

    /**
     * Create record record.
     *
     * @param record the record
     * @return the record
     */
    @PostMapping("/records")
    public Record createRecord( @RequestBody Record record) {
        return recordsRepository.save(record);
    }

    /**
     * Update record response entity.
     *
     * @param recordId the record id
     * @param recordDetails the record details
     * @return the response entity
     * @throws ResourceNotFoundException the resource not found exception
     */
    @PutMapping("/records/{id}")
    public ResponseEntity<Record> updateRecord(
            @PathVariable(value = "id") Long recordId, @RequestBody Record recordDetails)
            throws ResourceNotFoundException {

        Record record =
                recordsRepository
                        .findById(recordId)
                        .orElseThrow(() -> new ResourceNotFoundException("Record not found on :: " + recordId));

        record.setDescription(recordDetails.getDescription());
        record.setStatus(recordDetails.getStatus());
        record.setRandom(recordDetails.getRandom());
        record.setProvince(recordDetails.getProvince());
        record.setUpdatedAt(new Date());
        final Record updatedRecord = recordsRepository.save(record);
        return ResponseEntity.ok(updatedRecord);
    }

    /**
     * Delete record map.
     *
     * @param recordId the record id
     * @return the map
     * @throws Exception the exception
     */
    @DeleteMapping("/record/{id}")
    public Map<String, Boolean> deleteRecord(@PathVariable(value = "id") Long recordId) throws Exception {
        Record record =
                recordsRepository
                        .findById(recordId)
                        .orElseThrow(() -> new ResourceNotFoundException("Record not found on :: " + recordId));

        recordsRepository.delete(record);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
