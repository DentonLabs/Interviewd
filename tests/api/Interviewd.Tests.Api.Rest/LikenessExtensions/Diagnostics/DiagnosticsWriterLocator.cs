﻿namespace Interviewd.Tests.Api.Rest.LikenessExtensions.Diagnostics
{
    public static class DiagnosticsWriterLocator
    {
        private static object _syncRoot = new object();
        private static IDiagnosticsWriter _diagnosticsWriter;

        public static IDiagnosticsWriter DiagnosticsWriter
        {
            get
            {
                if (_diagnosticsWriter == null)
                {
                    lock (_syncRoot)
                    {
                        if (_diagnosticsWriter == null)
                        {
                            _diagnosticsWriter = CreateDefaultImplementation();
                        }
                    }
                }

                return _diagnosticsWriter;
            }
            set
            {
                lock (_syncRoot)
                {
                    _diagnosticsWriter = value;
                }
            }
        }

        private static IDiagnosticsWriter CreateDefaultImplementation()
        {
            return new DefaultDiagnosticsWriter();
        }
    }
}