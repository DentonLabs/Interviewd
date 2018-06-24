using System.Collections.Generic;
using IdentityServer4.Models;
using IdentityServer4.Test;

namespace Interviewd.Authentication
{
    public class Config
    {
        public static IEnumerable<ApiResource> GetApiResources()
        {
            return new List<ApiResource>
            {
                new ApiResource("interviewd", "Interviewd")
            };
        }

        public static IEnumerable<Client> GetClients()
        {
            return new List<Client>
            {
                new Client
                {
                    ClientId = "client-credentials",
                    AllowedGrantTypes = GrantTypes.ClientCredentials,
                    ClientSecrets =
                    {
                        new Secret("client-credentials-secret".Sha256())
                    },
                    AllowedScopes = { "interviewd" }
                },
                new Client
                {
                    ClientId = "resource-owner",
                    AllowedGrantTypes = GrantTypes.ResourceOwnerPassword,
                    ClientSecrets =
                    {
                        new Secret("resource-owner-secret".Sha256())
                    },
                    AllowedScopes = {"interviewd" }
                }
            };
        }

        public static List<TestUser> GetUsers()
        {
            return new List<TestUser>
            {
                new TestUser
                {
                    SubjectId = "1",
                    Username = "alex",
                    Password = "password"
                },
                new TestUser
                {
                    SubjectId = "2",
                    Username = "erin",
                    Password = "password"
                }
            };
        }
    }
}