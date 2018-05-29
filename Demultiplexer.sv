module Demultiplexer#(parameter WIDTH,
                    parameter SELECT_WIDTH)
                    (input logic [WIDTH - 1 : 0]data_in,
                     input logic [SELECT_WIDTH - 1 : 0]index,
                    output logic [WIDTH - 1 : 0]data_out[2**SELECT_WIDTH]);
        genvar i;
        generate 
            for (i = 0; i < (2**SELECT_WIDTH); i++) begin:forloop
                assign data_out[i] = (index == i) ? data_in : '0;
            end
        endgenerate
endmodule