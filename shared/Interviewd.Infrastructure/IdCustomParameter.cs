using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using Dapper;

namespace Interviewd.Infrastructure
{
    class IdCustomParameter : SqlMapper.ICustomQueryParameter
    {
        IEnumerable<int> Ids;

        public IdCustomParameter(IEnumerable<int> ids)
        {
            Ids = ids;
        }

        public void AddParameter(IDbCommand command, string name)
        {
            var sqlCommand = (SqlCommand)command;
            sqlCommand.CommandType = CommandType.StoredProcedure;

            var ids = new List<Microsoft.SqlServer.Server.SqlDataRecord>();

            // Create an SqlMetaData object that describes our table type.
            Microsoft.SqlServer.Server.SqlMetaData[] tvpDefinition = { new Microsoft.SqlServer.Server.SqlMetaData("Id", SqlDbType.Int) };

            foreach (var id in Ids)
            {
                // Create a new record, using the metadata array above.
                var rec = new Microsoft.SqlServer.Server.SqlDataRecord(tvpDefinition);
                rec.SetInt32(0, id);    // Set the value.
                ids.Add(rec);      // Add it to the list.
            }

            // Add the table parameter.
            var p = sqlCommand.Parameters.Add(name, SqlDbType.Structured);
            p.Direction = ParameterDirection.Input;
            p.TypeName = "Ids";
            p.Value = ids;
        }
    }
}