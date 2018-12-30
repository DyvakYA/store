<div class="modal fade" id="client-pay-modal" tabindex="-1" role="dialog" aria-labelledby="call-modal" aria-hidden="true">
<div class="modal-dialog">
<div class="modal-content">
<div class="modal-header">
<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
<h4 class="modal-title my-modal-label">Please enter your payment information below</h4>
</div>
<div class="modal-body">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <div class="alert alert-info" role="alert">
                    <p>Please enter your card details below.</p>
                </div>
                <hr/>
                <!-- Name -->
                <div class="form-group">
                    <label class="control-label" for="username">Card Holder's Name</label>

                    <div class="controls">
                        <input type="text" id="username" name="username" placeholder="" class="form-control">
                    </div>
                </div>

                <!-- Card Number -->
                <div class="form-group">
                    <label class="control-label" for="card_number">Card Number</label>

                    <div class="controls">
                        <input type="text" id="card_number" name="card_number" placeholder=""
                               class="form-control">
                    </div>
                </div>
                <hr/>
                <!-- Expiry-->
                <div class="form-group">
                    <label class="control-label" for="expiry_month">Card Expiry Date</label>

                    <div class="row">
                        <div class="col-sm-4">
                            <select class="form-control" name="expiry_month" id="expiry_month">
                                <option>----</option>
                                <option value="01">Jan (01)</option>
                                <option value="02">Feb (02)</option>
                                <option value="03">Mar (03)</option>
                                <option value="04">Apr (04)</option>
                                <option value="05">May (05)</option>
                                <option value="06">June (06)</option>
                                <option value="07">July (07)</option>
                                <option value="08">Aug (08)</option>
                                <option value="09">Sep (09)</option>
                                <option value="10">Oct (10)</option>
                                <option value="11">Nov (11)</option>
                                <option value="12">Dec (12)</option>
                            </select>
                        </div>
                        <div class="col-sm-3">
                            <select class="form-control" name="expiry_year">
                                <option value="">-----</option>
                                <option value="13">2013</option>
                                <option value="14">2014</option>
                                <option value="15">2015</option>
                                <option value="16">2016</option>
                                <option value="17">2017</option>
                                <option value="18">2018</option>
                                <option value="19">2019</option>
                                <option value="20">2020</option>
                                <option value="21">2021</option>
                                <option value="22">2022</option>
                                <option value="23">2023</option>
                            </select>
                        </div>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-sm-3">
                        <!-- CVV -->
                        <div class="form-group">
                            <label class="control-label" for="password_confirm">Card CVV</label>


                            <input type="password" id="password_confirm"
                                   name="password_confirm" placeholder=""
                                   class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        <!-- Submit -->
                        <div class="form-group">
                            <button class="btn btn-success">Pay Now</button>
                        </div>
                    </div>
                    <div class="col-sm-4"></div>
                    <div class="col-sm-5">
                        <!-- Space for Logo -->
                    </div>
                </div>

            </div>

        </div>

    </div>
</div> <!--end modal-body-->
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
</div>
</div>
</div>
</div>